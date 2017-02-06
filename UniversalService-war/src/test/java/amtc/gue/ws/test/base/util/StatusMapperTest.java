package amtc.gue.ws.test.base.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.inout.Status;
import amtc.gue.ws.base.util.StatusMapper;

/**
 * Test class for the StatusMapper
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StatusMapperTest {

	private IDelegatorOutput bdOutput;

	@Before
	public void setUp() {
		bdOutput = null;
	}

	@Test
	public void testbuildStatusForDelegatorOutputUsingNullInput() {
		assertNull(StatusMapper.buildStatusForDelegatorOutput(bdOutput));
	}

	@Test
	public void testbuildStatusForDelegatorOutputUsingSimpleInput() {
		bdOutput = new DelegatorOutput();
		Status createdStatus = StatusMapper
				.buildStatusForDelegatorOutput(bdOutput);
		assertNotNull(createdStatus);
		assertEquals(0, createdStatus.getStatusCode());
		assertNull(createdStatus.getStatusMessage());
	}
}
