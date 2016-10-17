package rwt.fview;

import java.io.File;
import java.io.RandomAccessFile;

class CachedBytes {
   public final long which;
   public final byte[] bytes;

   public CachedBytes(long w, byte[] b) { which = w; bytes = b; }
}

class CircularCache {
    private final CachedBytes[] cache;
    private int idx;

    CircularCache(int size) {
         idx = 0;
         cache = new CachedBytes[size];
         for(int idx = 0; idx < cache.length; ++idx) {
               cache[idx] = new CachedBytes(-1L, null);
         }
    }


    byte[] lookup(long which) {
       byte[] answer = null;
       for(int idx = 0; idx < cache.length; ++idx) {
           if(cache[idx].which == which) {
		answer = cache[idx].bytes;
                break;
           }
       } 
       return answer;
    }

    void insert(long which, byte[] bytes) {
        cache[idx] = new CachedBytes(which, bytes);
        idx++;
        if(idx == cache.length) {
            idx = 0;
        }
    }

    void clear() {
        for(int idx = 0; idx < cache.length; ++idx) {
            cache[idx] = new CachedBytes(-1, null);
        }
    }
}

/**
 * Created by richa on 10/11/2016.
 */
class FileData {

    private final RandomAccessFile src;
    private final CircularCache cache;
    private int chunkSize;

    FileData(File file, int cSize) throws java.io.IOException {
         src = new RandomAccessFile(file, "r");
         cache = new CircularCache(5);
         chunkSize = cSize;
    }


    void changeChunkSize(int newSz) {
        chunkSize = newSz;
        cache.clear();
    }

    private byte[] readChunk(long which) throws java.io.IOException {
        byte[] ans = new byte[chunkSize];
        src.seek( chunkSize * which );
        src.readFully(ans);
        return ans;
    }

    byte[] fetchData(long which) throws java.io.IOException {
         byte[] answer = cache.lookup(which);
         if(answer == null) {
              answer = readChunk(which);
              cache.insert(which, answer);
         }
         return answer;
    }

    void close() {
        try {
           src.close();
        } catch(Exception e) {  e.printStackTrace(); }
    }
}
