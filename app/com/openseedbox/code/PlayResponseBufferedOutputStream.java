package com.openseedbox.code;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import play.mvc.Http.Response;

/**
 * Wraps the normal Play output stream and flushes it every (bufferSize, default 4096) bytes
 * This is so you can use the StreamUtils.copy() functions without Play buffering the
 * entire response in memory
 * @author Erin Drummond
 */
public class PlayResponseBufferedOutputStream extends ByteArrayOutputStream {

		private Response r;
		private int bufferSize;

		public PlayResponseBufferedOutputStream(Response r) {
			this(r, 4096);
		}
		
		public PlayResponseBufferedOutputStream(Response r, int bufferSize) {
			super(bufferSize);
			this.bufferSize = bufferSize;
			this.r = r;
		}		

		@Override
		public synchronized void write(byte[] b, int off, int len) {
			super.write(b, off, len);
			if (count >= bufferSize) {
				flushBuffer();
			}
		}

		@Override
		public void close() throws IOException {
			flushBuffer(); //get the last few bytes
		}

		private void flushBuffer() {			
			r.writeChunk(this.toByteArray());			
			reset();			
		}	
}
