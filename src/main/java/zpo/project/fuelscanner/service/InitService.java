package zpo.project.fuelscanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zpo.project.fuelscanner.model.FuelSum;
import zpo.project.fuelscanner.model.Fuelling;
import zpo.project.fuelscanner.model.User;
import zpo.project.fuelscanner.repository.FuelSumRepo;
import zpo.project.fuelscanner.repository.FuellingRepository;
import zpo.project.fuelscanner.repository.UserRepository;

import javax.annotation.PostConstruct;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class InitService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FuelSumRepo fuelSumRepo;

    @Autowired
    FuellingRepository fuellingRepository;

    @Autowired
    FuellingService fuellingService;

    @PostConstruct
    public void init() {
        User u1 = new User(0L, "janKowalski", "Jan Kowalski", "Qwerty123" , "janKowalski@gmail.com");
        User u2 = new User(0L, "adamNowak", "Adam Nowak", "Qwerty123" , "adamNowak@gmail.com");
        User u3 = new User(0L, "aniaNowicka", "Ania Nowicka", "Qwerty123" , "aniaNowicka@gmail.com");

        u1 = userRepository.save(u1);
        u2 = userRepository.save(u2);
        u3 = userRepository.save(u3);

        FuelSum fuelSum1 = new FuelSum(0L, 10.05,null, 51.54);
        FuelSum fuelSum2 = new FuelSum(0L, 8.95,null, 42.46);
        FuelSum fuelSum3 = new FuelSum(0L, 20.12,null, 102.57);
        FuelSum fuelSum4 = new FuelSum(0L, 15.45,null, 68.95);
        FuelSum fuelSum5 = new FuelSum(0L, 22.34,null, 120.12);
        FuelSum fuelSum6 = new FuelSum(0L, 6.97,null, 34.07);
        FuelSum fuelSum7 = new FuelSum(0L, 18.23,null, 88.39);
        FuelSum fuelSum8 = new FuelSum(0L, 15.13,null, 65.85);
        FuelSum fuelSum9 = new FuelSum(0L, 25.92,null, 127.39);

        fuelSum1 = fuelSumRepo.save(fuelSum1);
        fuelSum2 = fuelSumRepo.save(fuelSum2);
        fuelSum3 = fuelSumRepo.save(fuelSum3);
        fuelSum4 = fuelSumRepo.save(fuelSum4);
        fuelSum5 = fuelSumRepo.save(fuelSum5);
        fuelSum6 = fuelSumRepo.save(fuelSum6);
        fuelSum7 = fuelSumRepo.save(fuelSum7);
        fuelSum8 = fuelSumRepo.save(fuelSum8);
        fuelSum9 = fuelSumRepo.save(fuelSum9);

        Fuelling fuelling1 = new Fuelling(0L, u1, fuelSum1, LocalDate.now().minusDays(18));
        Fuelling fuelling2 = new Fuelling(0L, u1, fuelSum2, LocalDate.now().minusDays(14));
        Fuelling fuelling3 = new Fuelling(0L, u1, fuelSum3, LocalDate.now().minusDays(12));
        Fuelling fuelling4 = new Fuelling(0L, u2, fuelSum4, LocalDate.now().minusDays(45));
        Fuelling fuelling5 = new Fuelling(0L, u2, fuelSum5, LocalDate.now().minusDays(35));
        Fuelling fuelling6 = new Fuelling(0L, u2, fuelSum6, LocalDate.now().minusDays(9));
        Fuelling fuelling7 = new Fuelling(0L, u3, fuelSum7, LocalDate.now().minusDays(46));
        Fuelling fuelling8 = new Fuelling(0L, u3, fuelSum8, LocalDate.now().minusDays(23));
        Fuelling fuelling9 = new Fuelling(0L, u3, fuelSum9, LocalDate.now().minusDays(11));

        fuellingRepository.saveAll(Arrays.asList(fuelling1, fuelling2, fuelling3,
                fuelling4, fuelling5, fuelling6, fuelling7, fuelling8, fuelling9));

    }
}
