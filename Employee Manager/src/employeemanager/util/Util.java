package employeemanager.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import employeemanager.dto.Employee;

public class Util {

    private static final Logger logger = LogManager.getLogger(Util.class);

    public static Boolean isEmail(String email) {
        logger.trace("Validating email: {}", email);

        if (email.matches("^[a-z]+[a-z0-9]+(.[a-z0-9]+)*@[a-z]+(.[a-z]+)+$")) {
            logger.info("Email validation passed for: {}", email);
            return true;
        }

        logger.warn("Invalid email format: {}", email);
        return false;
    }

    public static Boolean isId(String id) {
        logger.trace("Validating ID: {}", id);

        if (id.matches("^[0-9]+$")) {
            logger.info("ID validation passed for: {}", id);
            return true;
        }

        logger.warn("Invalid ID format: {}", id);
        return false;
    }

    public static Boolean isId(String id, List<Employee> emplist) {
        logger.trace("Validating unique ID: {}", id);

        if (id.matches("^[0-9]+$")) {
            for (Employee emp : emplist) {
                if (emp.getID().equals(id)) {
                    logger.warn("Duplicate ID found: {}", id);
                    return false;
                }
            }
            logger.info("Unique ID validation passed for: {}", id);
            return true;
        }

        logger.warn("Invalid ID format: {}", id);
        return false;
    }

    public static Boolean isPhn(String phn) {
        logger.trace("Validating phone number: {}", phn);

        if (phn.matches("^[0-9]{10}$")) {
            logger.info("Phone number validation passed for: {}", phn);
            return true;
        }

        logger.warn("Invalid phone number: {}", phn);
        return false;
    }

    public static Boolean isName(String fname, String lname) {
        logger.trace("Validating names: fname={}, lname={}", fname, lname);

        if (fname.matches("^[A-Za-z]+$") && lname.matches("^[A-Za-z]+$")) {
            logger.info("Name validation passed: {} {}", fname, lname);
            return true;
        }

        logger.warn("Invalid name(s): fname={}, lname={}", fname, lname);
        return false;
    }

    public static Boolean isJoindate(String joindate) {
        logger.trace("Validating join date: {}", joindate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(joindate, formatter);
            logger.info("Join date validation passed: {}", joindate);
            return true;
        } catch (Exception e) {
            logger.error("Join date validation failed for: {}", joindate, e);
            return false;
        }
    }

    public static Boolean isstatus(String status) {
        logger.trace("Validating status: {}", status);

        if (status.equalsIgnoreCase("true") || status.equalsIgnoreCase("false")) {
            logger.info("Status validation passed: {}", status);
            return true;
        }

        logger.warn("Invalid status value: {}", status);
        return false;
    }
}
