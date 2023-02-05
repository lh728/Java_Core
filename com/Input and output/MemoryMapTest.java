package com_second.Input_and_output;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.CRC32;

public class MemoryMapTest {
    public static long checksumInputStream(Path filename) throws IOException {
        try (InputStream in = Files.newInputStream(filename)) {
            var crc = new CRC32();
            int c;
            while ((c = in.read()) != -1) {
                crc.update(c);
            }
            return crc.getValue();
        }
    }
    public static long checksumBufferedInputStream(Path filename) throws IOException {
        try (InputStream in = new BufferedInputStream(Files.newInputStream(filename))) {
            var crc = new CRC32();
            int c;
            while ((c = in.read()) != -1) {
                crc.update(c);
            }
            return crc.getValue();
        }
    }

    public static long checksumRandomAccessFile(Path filename) throws IOException {
        try (var file = new RandomAccessFile(filename.toFile(),"r")) {
            long length = file.length();
            var crc = new CRC32();
            for (long p = 0;p < length;p++){
                file.seek(p);
                int c = file.readByte();
                crc.update(c);
            }
            return crc.getValue();
        }
    }

    public static long checksumMappedFile(Path filename) throws IOException {
        try (var channel = FileChannel.open(filename)) {

            var crc = new CRC32();
            int length = (int) channel.size();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);
            for (int p = 0;p < length;p++){
                int c = map.get(p);
                crc.update(c);
            }
            return crc.getValue();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(" Input Stream: ");
        long start = System.currentTimeMillis();
        Path path = Paths.get(args[0]);
        long l = checksumInputStream(path);
        long end = System.currentTimeMillis();
        System.out.println(Long.toHexString(l));
        System.out.println((end - start) + "milliseconds");

        System.out.println(" Buffered Stream: ");
        start = System.currentTimeMillis();
        path = Paths.get(args[0]);
        l = checksumBufferedInputStream(path);
        end = System.currentTimeMillis();
        System.out.println(Long.toHexString(l));
        System.out.println((end - start) + "milliseconds");

        System.out.println(" Random Access Stream: ");
        start = System.currentTimeMillis();
        path = Paths.get(args[0]);
        l = checksumRandomAccessFile(path);
        end = System.currentTimeMillis();
        System.out.println(Long.toHexString(l));
        System.out.println((end - start) + "milliseconds");

        System.out.println(" MAPPED Stream: ");
        start = System.currentTimeMillis();
        path = Paths.get(args[0]);
        l = checksumMappedFile(path);
        end = System.currentTimeMillis();
        System.out.println(Long.toHexString(l));
        System.out.println((end - start) + "milliseconds");
    }


}
