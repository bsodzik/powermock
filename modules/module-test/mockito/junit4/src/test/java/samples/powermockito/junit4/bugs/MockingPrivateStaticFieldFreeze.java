package samples.powermockito.junit4.bugs;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 *
 */
@FixMethodOrder(MethodSorters.JVM)
@RunWith(PowerMockRunner.class)
public class MockingPrivateStaticFieldFreeze {

    private Logger loggerMock;
    private AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    @Before
    public void setUp() {
        loggerMock = mock(Logger.class);
        doAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                return atomicBoolean.get();
            }
        }).when(loggerMock).isTraceEnabled();

        mockStatic(LoggerFactory.class);

        doReturn(loggerMock).when(LoggerFactory.class);
        LoggerFactory.getLogger(Fuu.class);
        //Whitebox.setInternalState(Fuu.class, loggerMock);
    }



    @Test
    public void testMockingPrivateStaticFieldFreezeStateOfMockAfterFirstUse_firstUse() throws Exception {

        // given
        atomicBoolean.set( false);

        // when

        assertFalse("Expected false",Fuu.doStuff());

    }

    @Test
    public void testMockingPrivateStaticFieldFreezeStateOfMockAfterFirstUse_secondUse() throws Exception {

        // given
        atomicBoolean.set(true);

        // when
        assertTrue("Expected true", Fuu.doStuff());

    }
}
