package rwt.fview

import java.io.File
import java.io.RandomAccessFile

private class CachedBytes(val which : Long, val bytes : ByteArray)

private class CircularCache(sz : Int) {
    private val cache = Array<CachedBytes>(sz, { CachedBytes(-1, ByteArray(0))})
    private var idx = 0

    fun lookup(which : Long) = cache.find { it.which == which }?.bytes

    fun insert(which : Long, bytes : ByteArray) {
        cache[idx] = CachedBytes(which, bytes)
        idx++
        if(idx == cache.size) {
            idx = 0
        }
    }

    fun clear() {
        for(i in cache.indices) {
            cache[i] = CachedBytes(-1, ByteArray(0))
        }
    }
}

/**
 * Created by richa on 10/11/2016.
 */
internal class FileData(file : File, private var chunkSize : Int) {
    private val src = RandomAccessFile(file, "r")
    private val cache = CircularCache(5)

    fun changeChunkSize(newSz : Int) {
        chunkSize = newSz
        cache.clear()
    }

    private fun readChunk(which : Long) : ByteArray {
        val ans = ByteArray(chunkSize)
        src.seek( chunkSize.toLong() * which )
        src.readFully(ans)
        return ans
    }

    fun fetchData(which: Long) : ByteArray {
         return cache.lookup(which) ?: readChunk(which).apply { cache.insert(which, this) }
    }

    fun close() {
        src.close()
    }
}