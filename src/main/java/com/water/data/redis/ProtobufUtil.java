package com.water.data.redis;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;

import java.io.*;

/**
 * Created by zhangmiaojie on 2018/1/12.
 */
public class ProtobufUtil {

    public static byte[] toArryByte(Object obj, Class clz) {
        if (obj != null) {
            Codec simpleTypeCodec = ProtobufProxy.create(clz);
            try {
                // 序列化
                return simpleTypeCodec.encode(obj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static byte[] ObjectToByte(java.lang.Object obj) {
        byte[] bytes = null;
        try {
            // object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return bytes;
    }

    public static Object ByteToObject(byte[] bytes) {
        Object obj = null;
        if (bytes != null) {
            try {
                // bytearray to object
                ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
                ObjectInputStream oi = new ObjectInputStream(bi);

                obj = oi.readObject();
                bi.close();
                oi.close();
            } catch (Exception e) {
                System.out.println("translation" + e.getMessage());
                e.printStackTrace();
            }
        }
        return obj;
    }
}
