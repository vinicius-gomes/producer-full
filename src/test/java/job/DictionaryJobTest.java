package job;

import com.b2wdigital.lets.juvenal.producer.exceptions.PayloadValidationException;
import com.b2wdigital.lets.juvenal.producer.job.DictionaryJob;
import com.b2wdigital.lets.juvenal.producer.service.DictionaryService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.mockito.Mockito.times;

@QuarkusTest
class DictionaryJobTest {

    @Inject
    DictionaryJob dictionaryJob;
    @InjectMock
    DictionaryService dictionaryService;

    @Test
    void updateAllDictionaries() throws PayloadValidationException {
        dictionaryJob.updateAllDictionaries();
        Mockito.verify(dictionaryService, times(1)).updateDictionaries();
    }
}
