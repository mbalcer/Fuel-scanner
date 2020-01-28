package zpo.project.fuelscanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zpo.project.fuelscanner.model.Counter;
import zpo.project.fuelscanner.model.Receipt;
import zpo.project.fuelscanner.model.User;
import zpo.project.fuelscanner.repository.CounterRepository;
import zpo.project.fuelscanner.repository.ReceiptRepository;
import zpo.project.fuelscanner.repository.UserRepository;

import javax.annotation.PostConstruct;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class InitService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    CounterRepository counterRepository;

    @PostConstruct
    public void init() {
        User u1 = new User(0L, "janKowalski", "Jan Kowalski", "Qwerty123", "janKowalski@gmail.com");
        User u2 = new User(0L, "adamNowak", "Adam Nowak", "Qwerty123", "adamNowak@gmail.com");
        User u3 = new User(0L, "aniaNowicka", "Ania Nowicka", "Qwerty123", "aniaNowicka@gmail.com");

        u1 = userRepository.save(u1);
        u2 = userRepository.save(u2);
        u3 = userRepository.save(u3);

        Receipt receipt1 = new Receipt(0L, "", "", LocalDate.now().minusDays(18), 10.05, 51.54 / 10.05, 51.54, u1);
        Receipt receipt2 = new Receipt(0L, "", "", LocalDate.now().minusDays(14), 8.95, 42.46 / 8.95, 42.46, u1);
        Receipt receipt3 = new Receipt(0L, "", "", LocalDate.now().minusDays(12), 20.12, 102.57 / 20.12, 102.57, u1);
        Receipt receipt4 = new Receipt(0L, "", "", LocalDate.now().minusDays(45), 15.45, 68.95 / 15.45, 68.95, u2);
        Receipt receipt5 = new Receipt(0L, "", "", LocalDate.now().minusDays(35), 22.34, 120.12 / 22.34, 120.12, u2);
        Receipt receipt6 = new Receipt(0L, "", "", LocalDate.now().minusDays(9), 6.97, 34.07 / 6.97, 34.07, u2);
        Receipt receipt7 = new Receipt(0L, "", "", LocalDate.now().minusDays(46), 18.23, 88.3954 / 18.23, 88.39, u3);
        Receipt receipt8 = new Receipt(0L, "", "", LocalDate.now().minusDays(23), 15.13, 65.85 / 15.13, 65.85, u3);
        Receipt receipt9 = new Receipt(0L, "", "", LocalDate.now().minusDays(11), 25.92, 127.39 / 25.92, 127.3954, u3);

        receiptRepository.saveAll(Arrays.asList(receipt1, receipt2, receipt3,
                receipt4, receipt5, receipt6, receipt7, receipt8, receipt9));

        Counter c1 = new Counter(0L, 135567.00, LocalDate.now().minusDays(36), 14.00, u1);
        Counter c2 = new Counter(0L, 135678.00, LocalDate.now().minusDays(8), 7.00, u1);
        Counter c3 = new Counter(0L, 235753.00, LocalDate.now().minusDays(48), 11.00, u2);
        Counter c4 = new Counter(0L, 237765.00, LocalDate.now().minusDays(15), 9.00, u2);
        Counter c5 = new Counter(0L, 58554.00, LocalDate.now().minusDays(40), 4.00, u3);
        Counter c6 = new Counter(0L, 59326.00, LocalDate.now().minusDays(7), 6.00, u3);

        counterRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6));

    }
}
