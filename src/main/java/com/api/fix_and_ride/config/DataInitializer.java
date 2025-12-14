package com.api.fix_and_ride.config;

import com.api.fix_and_ride.entity.AdminUserEntity;
import com.api.fix_and_ride.entity.BookingEntity;
import com.api.fix_and_ride.entity.BookingStatus;
import com.api.fix_and_ride.entity.UserEntity;
import com.api.fix_and_ride.repository.AdminUserRepository;
import com.api.fix_and_ride.repository.BookingRepository;
import com.api.fix_and_ride.repository.ServiceItemRepository;
import com.api.fix_and_ride.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepo;
    private final AdminUserRepository adminRepo;
    private final ServiceItemRepository serviceRepo;
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

            var user1 = userRepo.findByEmail("cena@fix.com").orElseThrow();
            var user2 = userRepo.findByEmail("wick@fix.com").orElseThrow();

            var taxi = serviceRepo.findByKeyName("taxi").orElseThrow();
            var repairs = serviceRepo.findByKeyName("repairs").orElseThrow();

            // Booking 1: tomorrow 10:00 - 12:00, cost 50.00
            BookingEntity booking1 = new BookingEntity();
            booking1.setUser(user1);
            booking1.setService(taxi);
            booking1.setStartDateTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0));
            booking1.setEndDateTime(LocalDateTime.now().plusDays(1).withHour(12).withMinute(0));
            booking1.setCost(new BigDecimal("50.00"));
            booking1.setStatus(BookingStatus.PENDING);

            bookingRepo.save(booking1);

            // Booking 2: in 3 days 14:00 - 16:30, cost 120.50
            BookingEntity booking2 = new BookingEntity();
            booking2.setUser(user2);
            booking2.setService(repairs);
            booking2.setStartDateTime(LocalDateTime.now().plusDays(3).withHour(14).withMinute(0));
            booking2.setEndDateTime(LocalDateTime.now().plusDays(3).withHour(16).withMinute(30));
            booking2.setCost(new BigDecimal("120.50"));
            booking2.setStatus(BookingStatus.ACCEPTED); // or CONFIRMED if your enum has it

            bookingRepo.save(booking2);
        }


        System.out.println("[DataInitializer] Initial data loaded.");
    }
}
