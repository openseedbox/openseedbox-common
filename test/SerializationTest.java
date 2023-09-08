
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openseedbox.backend.INodeStatus;
import com.openseedbox.backend.NodeStatus;
import com.openseedbox.gson.AccessorBasedTypeAdapterFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class SerializationTest {
	
	@Test
	public void testAccessorBasedJsonSerialization() {
		INodeStatus status = new NodeStatus("uptime", 1000, 2000, "baseDir", false, null);
		String serialized = getGson().toJson(status);
		//should be something like: "{"uptime":"uptime","free-space-bytes":1000,"used-space-bytes":1000,"total-space-bytes":2000,"is-backend-installed":false,"base-directory":"baseDir","is-base-directory-writable":false,"is-backend-running":false}"
		assertTrue(serialized.contains("\"uptime\":\"uptime\""));
		assertTrue(serialized.contains("\"free-space-bytes\":1000"));
		assertTrue(serialized.contains("\"used-space-bytes\":1000"));
		assertTrue(serialized.contains("\"total-space-bytes\":2000"));
		assertTrue(serialized.contains("\"base-directory\":\"baseDir\""));
		assertTrue(serialized.contains("\"is-base-directory-writable\":false"));
	}
	
	@Test
	public void testAccessorBasedJsonDeserialization() {		
		String serialized = "{\"uptime\":\"uptime\",\"free-space-bytes\":1000,\"used-space-bytes\":1000,\"total-space-bytes\":2000,\"is-backend-installed\":false,\"base-directory\":\"baseDir\",\"is-base-directory-writable\":false,\"is-backend-running\":false}";
		INodeStatus status = getGson().fromJson(serialized, NodeStatus.class);
		assertEquals("uptime", status.getUptime());
		assertEquals(1000, status.getFreeSpaceBytes());
		assertEquals(1000, status.getUsedSpaceBytes());
		assertEquals(2000, status.getTotalSpaceBytes());
		assertEquals("baseDir", status.getBaseDirectory());
		assertFalse(status.isBaseDirectoryWritable());
	}	
	
	private Gson getGson() {
		return new GsonBuilder().registerTypeAdapterFactory(new AccessorBasedTypeAdapterFactory()).create();
	}
	
}
