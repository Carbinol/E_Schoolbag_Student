package com.example.studentebag;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import co.junwei.bswabe.Bswabe;
import co.junwei.bswabe.BswabeCph;
import co.junwei.bswabe.BswabeCphKey;
import co.junwei.bswabe.BswabeElementBoolean;
import co.junwei.bswabe.BswabeMsk;
import co.junwei.bswabe.BswabePrv;
import co.junwei.bswabe.BswabePub;
import co.junwei.bswabe.SerializeUtils;
import co.junwei.cpabe.Common;
import co.junwei.cpabe.policy.LangPolicy;
import it.unisa.dia.gas.jpbc.Element;

public class Cpabe {
    public Cpabe() {
    }

    public void setup(String pubfile, String mskfile) throws IOException, ClassNotFoundException {
        BswabePub pub = new BswabePub();
        BswabeMsk msk = new BswabeMsk();
        Bswabe.setup(pub, msk);
        byte[] pub_byte = SerializeUtils.serializeBswabePub(pub);
        Common.spitFile(pubfile, pub_byte);
        byte[] msk_byte = SerializeUtils.serializeBswabeMsk(msk);
        Common.spitFile(mskfile, msk_byte);
    }

    public void keygen(String pubfile, String prvfile, String mskfile, String attr_str) throws NoSuchAlgorithmException, IOException {
        byte[] pub_byte = Common.suckFile(pubfile);
        BswabePub pub = SerializeUtils.unserializeBswabePub(pub_byte);
        byte[] msk_byte = Common.suckFile(mskfile);
        BswabeMsk msk = SerializeUtils.unserializeBswabeMsk(pub, msk_byte);
        String[] attr_arr = LangPolicy.parseAttribute(attr_str);
        BswabePrv prv = Bswabe.keygen(pub, msk, attr_arr);
        byte[] prv_byte = SerializeUtils.serializeBswabePrv(prv);
        Common.spitFile(prvfile, prv_byte);
    }

    public void enc(String pubfile, String policy, String inputfile, String encfile) throws Exception {
        byte[] pub_byte = Common.suckFile(pubfile);
        BswabePub pub = SerializeUtils.unserializeBswabePub(pub_byte);
        BswabeCphKey keyCph = Bswabe.enc(pub, policy);
        BswabeCph cph = keyCph.cph;
        Element m = keyCph.key;
        System.err.println("m = " + m.toString());
        if (cph == null) {
            System.out.println("Error happed in enc");
            System.exit(0);
        }

        byte[] cphBuf = SerializeUtils.bswabeCphSerialize(cph);
        byte[] plt = Common.suckFile(inputfile);
        byte[] aesBuf = AESCoder.encrypt(m.toBytes(), plt);
        Common.writeCpabeFile(encfile, cphBuf, aesBuf);
    }

    public void dec(String pubfile, String prvfile, String encfile, String decfile) throws Exception {
        byte[] pub_byte = Common.suckFile(pubfile);
        BswabePub pub = SerializeUtils.unserializeBswabePub(pub_byte);
        byte[][] tmp = Common.readCpabeFile(encfile);
        byte[] aesBuf = tmp[0];
        byte[] cphBuf = tmp[1];
        BswabeCph cph = SerializeUtils.bswabeCphUnserialize(pub, cphBuf);
        byte[] prv_byte = Common.suckFile(prvfile);
        BswabePrv prv = SerializeUtils.unserializeBswabePrv(pub, prv_byte);
        BswabeElementBoolean beb = Bswabe.dec(pub, prv, cph);
        System.err.println("e = " + beb.e.toString());
        if (beb.b) {
            byte[] plt = AESCoder.decrypt(beb.e.toBytes(), aesBuf);
            Common.spitFile(decfile, plt);
        } else {
            System.exit(0);
        }

    }

    public static class AESCoder {
        public AESCoder() {
        }

        private static byte[] getRawKey(byte[] seed) throws Exception {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(seed);
            kgen.init(128, sr);
            SecretKey skey = kgen.generateKey();
            byte[] raw = skey.getEncoded();
            return raw;
        }

        public static byte[] encrypt(byte[] seed, byte[] plaintext) throws Exception {
            byte[] raw = InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(seed, 16);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, skeySpec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return encrypted;
        }

        public static byte[] decrypt(byte[] seed, byte[] ciphertext) throws Exception {
//            byte[] raw = getRawKey(seed);
            byte[] raw = InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(seed, 16);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, skeySpec);
            byte[] decrypted = cipher.doFinal(ciphertext);
            return decrypted;
        }
    }

}

