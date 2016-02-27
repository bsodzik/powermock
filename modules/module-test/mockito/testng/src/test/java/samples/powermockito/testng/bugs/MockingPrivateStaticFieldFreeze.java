package samples.powermockito.testng.bugs;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 *
 */
@PrepareForTest({LoggerFactory.class, Logger.class})
public class MockingPrivateStaticFieldFreeze extends PowerMockTestCase {

    private Logger loggerMock;
    private AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    @BeforeMethod
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
