package com.api.fix_and_ride.config;

import com.api.fix_and_ride.entity.AdminUserEntity;
import com.api.fix_and_ride.entity.BookingEntity;
import com.api.fix_and_ride.entity.UserEntity;
import com.api.fix_and_ride.repository.AdminUserRepository;
import com.api.fix_and_ride.repository.BookingRepository;
import com.api.fix_and_ride.repository.ServiceRepository;
import com.api.fix_and_ride.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepo;
    private final AdminUserRepository adminRepo;
    private final ServiceRepository serviceRepo;
    private final BookingRepository bookingRepo;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {

        // ---- USER CREATION ----
        if (userRepo.count() == 0) {

            userRepo.save(new UserEntity(
                    "John Cena",
                    "Street 1",
                    "12B",
                    "1000",
                    "CityOne",
                    "cena@fix.com",
                    encoder.encode("password123")
            ));

            userRepo.save(new UserEntity(
                    "Jane Wick",
                    "Street 2",
                    "44",
                    "2000",
                    "CityTwo",
                    "wick@fix.com",
                    encoder.encode("password123")
            ));
        }

        // ---- ADMIN CREATION ----
        if (adminRepo.count() == 0) {
            AdminUserEntity admin = new AdminUserEntity(
                    "admin@fix-and-ride.com",
                    "admin123"
            );

            adminRepo.save(admin);
        }

        // ---------- BOOKINGS ----------
        if (bookingRepo.count() == 0) {

            var user1 = userRepo.findByEmail("cena@fix.com").get();
            var user2 = userRepo.findByEmail("wick@fix.com").get();

            var taxi = serviceRepo.findByKeyName("taxi").get();
            var repairs = serviceRepo.findByKeyName("repairs").get();

            bookingRepo.save(new BookingEntity(
                    user1, taxi, LocalDate.now().plusDays(1), "PENDING"
            ));

            bookingRepo.save(new BookingEntity(
                    user2, repairs, LocalDate.now().plusDays(3), "CONFIRMED"
            ));
        }

        System.out.println("[DataInitializer] Initial data loaded.");
    }
}
