package fun.suggoitanoshi.creditsimulator.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Year;
import java.util.List;
import java.util.stream.Stream;

class LoanTest{
  static Stream<String[]> combinationProvider() {
    return Stream.of("mobil", "motor").flatMap(type -> 
        Stream.of("baru", "bekas").map(age -> 
          new String[]{ type, age }
          )
        );
  }

  @ParameterizedTest
  @MethodSource("combinationProvider")
  void BuildValidLoan(String type, String age){
    final int YEAR = Year.now().getValue();
    final int TOTALLOAN = 100_000_000;
    final int TENOR = 6;
    final int DOWNPAYMENT = 35_000_000;
    Loan validLoan = new Loan.Builder()
      .setVehicleType(type)
      .setVehicleAge(age)
      .setVehicleYear(YEAR)
      .setTotalLoan(TOTALLOAN)
      .setTenor(TENOR)
      .setDownPayment(DOWNPAYMENT)
      .build();
    assertNotNull(validLoan, "Valid loan object for " + type + " " + age + " should not be null");
    assertEquals(type, validLoan.getType());
    assertEquals(age, validLoan.getAge());
    assertEquals(YEAR, validLoan.getYear());
    assertEquals(TENOR, validLoan.getTenor());
    assertEquals(DOWNPAYMENT, validLoan.getDownPayment());
  }

  @Test
  void BuildInvalidLoanByType(){
    assertThrows(IllegalArgumentException.class, () -> {
      new Loan.Builder()
        .setVehicleType("truk")
        .setVehicleAge("baru")
        .setVehicleYear(Year.now().getValue())
        .setTotalLoan(100_000_000)
        .setTenor(6)
        .setDownPayment(35_000_000)
        .build();
    }, "Invalid down payment for loan object, should be null");
  }

  @Test
  void BuildInvalidLoanByYear(){
    Loan invalidLoan = new Loan.Builder()
      .setVehicleType("mobil")
      .setVehicleAge("baru")
      .setVehicleYear(Year.now().getValue() - 2)
      .setTotalLoan(100_000_000)
      .setTenor(6)
      .setDownPayment(35_000_000)
      .build();
    assertNull(invalidLoan, "Invalid year for loan object, should be null");
  }

  @Test
  void BuildInvalidLoanByTenor(){
    assertThrows(IllegalArgumentException.class, () -> {
      new Loan.Builder()
        .setVehicleType("mobil")
        .setVehicleAge("baru")
        .setVehicleYear(Year.now().getValue() - 1)
        .setTotalLoan(100_000_000)
        .setTenor(7)
        .setDownPayment(35_000_000)
        .build();
    },
    "Invalid tenor for loan object, should be null");
  }

  @Test
  void BuildInvalidLoanByTotalLoan(){
    assertThrows(IllegalArgumentException.class, () -> {
      new Loan.Builder()
        .setVehicleType("mobil")
        .setVehicleAge("baru")
        .setVehicleYear(Year.now().getValue() - 1)
        .setTotalLoan(2_000_000_000)
        .setTenor(6)
        .setDownPayment(1_000_000_000)
        .build();
    }, "Invalid total loan for loan object, should be null");
  }

  @Test
  void BuildInvalidLoanByDownPayment(){
    Loan invalidLoan = new Loan.Builder()
      .setVehicleType("mobil")
      .setVehicleAge("baru")
      .setVehicleYear(Year.now().getValue() - 1)
      .setTotalLoan(100_000_000)
      .setTenor(6)
      .setDownPayment(1_000_000)
      .build();
    assertNull(invalidLoan, "Invalid down payment for loan object, should be null");
  }

  @Test
  void CalculateInterestPerYear(){
    final int TENOR = 6;
    Loan validLoan = new Loan.Builder()
      .setVehicleType("mobil")
      .setVehicleAge("baru")
      .setVehicleYear(Year.now().getValue())
      .setTotalLoan(100_000_000)
      .setTenor(TENOR)
      .setDownPayment(35_000_000)
      .build();
    assertNotNull(validLoan);
    assertEquals(validLoan.getBaseInterest(), 8.0f);
    List<Double> interestPerYear = validLoan.getInterestPerYear();
    assertEquals(TENOR, interestPerYear.size());
    double[] container = new double[TENOR];
    for(int i = 0; i < interestPerYear.size(); i++){
      container[i] = interestPerYear.get(i);
    }
    assertArrayEquals(new double[]{8.0f, 8.1f, 8.6f, 8.7f, 9.2f, 9.3f}, container, 0.001f);
  }

  @Test
  void CalculateTotalPaymentPerYear(){
    final int TENOR = 3;
    Loan validLoan = new Loan.Builder()
      .setVehicleType("mobil")
      .setVehicleAge("bekas")
      .setVehicleYear(Year.now().getValue())
      .setTotalLoan(100_000_000)
      .setTenor(TENOR)
      .setDownPayment(25_000_000)
      .build();
    assertNotNull(validLoan);
    List<Double> totalPaymentPerYear = validLoan.getTotalPaymentPerYear();
    assertEquals(TENOR, totalPaymentPerYear.size());
    double[] container = new double[TENOR];
    for(int i = 0; i < totalPaymentPerYear.size(); i++){
      container[i] = totalPaymentPerYear.get(i);
    }
    assertArrayEquals(new double[]{27_000_000f, 29_187_000f, 31_697_082f}, container, 0.005f);
  }
}
