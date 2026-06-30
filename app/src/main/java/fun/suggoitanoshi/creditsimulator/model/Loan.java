package fun.suggoitanoshi.creditsimulator.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Loan{
  private String type;
  private String age;
  private Integer year;
  private Integer totalLoan;
  private Integer tenor;
  private Integer downPayment;

  private Loan(Builder builder){
    this.type = builder.type;
    this.age = builder.age;
    this.year = builder.year;
    this.totalLoan = builder.totalLoan;
    this.tenor = builder.tenor;
    this.downPayment = builder.downPayment;
  }

  public String getType(){
    return this.type;
  }
  public String getAge(){
    return this.age;
  }
  public Integer getYear(){
    return this.year;
  }
  public Integer getTotalLoan(){
    return this.totalLoan;
  }
  public Integer getTenor(){
    return this.tenor;
  }
  public Integer getDownPayment(){
    return this.downPayment;
  }

  public Double getBaseInterest(){
    if(type == "motor"){
      return 9.0d;
    } else {
      return 8.0d;
    }
  }

  public List<Double> getInterestPerYear(){
    List<Double> interestPerYear = new ArrayList<>();

    interestPerYear.add(getBaseInterest());

    for(int i = 1; i < this.tenor; i++){
      Double lastInterest = interestPerYear.get(i-1);
      Double currentInterest = lastInterest;

      if(i%2 == 1) currentInterest += 0.1f;
      else currentInterest += 0.5f;

      interestPerYear.add(currentInterest);
    }
    return interestPerYear;
  }

  // precision loss, should not use Double for this.
  public List<Double> getTotalPaymentPerYear(){
    List<Double> totalPaymentPerYear = new ArrayList<>();
    List<Double> interestPerYears = this.getInterestPerYear();
    Double principal = (double)(this.totalLoan - this.downPayment);
    Double loanedAmount = principal * (1+interestPerYears.get(0)/100);
    Double installmentPerYear = loanedAmount / this.tenor;
    totalPaymentPerYear.add(installmentPerYear);
    for(int i = 1; i < this.tenor; i++){
      principal = loanedAmount - installmentPerYear;
      loanedAmount = principal * (1+interestPerYears.get(i)/100);
      installmentPerYear = loanedAmount / (this.tenor - i);
      totalPaymentPerYear.add(installmentPerYear);
    }
    return totalPaymentPerYear;
  }
  public List<Double> getTotalPaymentPerMonth(){
    List<Double> totalPaymentPerYear = getTotalPaymentPerYear();
    for(int i = 0; i < totalPaymentPerYear.size(); i++){
      totalPaymentPerYear.set(i, totalPaymentPerYear.get(i)/12);
    }
    return totalPaymentPerYear;
  }

  public static class Builder {
    private static final int MAX_LOAN = 1_000_000_000;

    private String type = null;
    private String age = null;
    private Integer year = null;
    private Integer totalLoan = null;
    private Integer tenor = null;
    private Integer downPayment = null;

    public Builder setVehicleType(String type){
      type = type.toLowerCase();
      if(type.equals("mobil") || type.equals("motor")){
        this.type = type;
        return this;
      } else {
        throw new IllegalArgumentException("Jenis kendaraan hanya mobil atau motor!");
      }
    }

    public Builder setVehicleAge(String age){
      age = age.toLowerCase();
      if(age.equals("baru") || age.equals("bekas")){
        this.age = age;
        return this;
      } else {
        throw new IllegalArgumentException("Kondisi kendaraan hanya bekas atau baru!");
      }
    }

    public Builder setVehicleYear(int year){
      if(year >= 0 && year < 9999){ // 4 digit restriction on year
        this.year = year;
        return this;
      } else {
        throw new IllegalArgumentException("Tahun kendaraan harus 4 digit!");
      }
    }

    public Builder setTotalLoan(int loan){
      if(loan > 0 && loan <= MAX_LOAN){
        this.totalLoan = loan;
        return this;
      } else {
        throw new IllegalArgumentException("Total pinjaman harus positif dibawah 1 milyar!");
      }
    }

    public Builder setTenor(int tenor){
      if(tenor > 0 && tenor <= 6){
        this.tenor = tenor;
        return this;
      } else {
        throw new IllegalArgumentException("Tenor harus antara 1 sampai 6 tahun!");
      }
    }

    public Builder setDownPayment(int downPayment){
      if(downPayment >= 0 || downPayment <= MAX_LOAN){
        this.downPayment = downPayment;
        return this;
      } else {
        throw new IllegalArgumentException("DP harus positif dibawah 1 milyar!");
      }
    }

    public Loan build(){
      if(this.type == null
          || this.age == null
          || this.year == null
          || this.totalLoan == null
          || this.tenor == null
          || this.downPayment == null) {
        return null;
      }
      if(this.age == "baru") {
        if(this.downPayment < 0.35d*this.totalLoan) {
          return null;
        }
        int currentYear = Year.now().getValue();
        if(currentYear < this.year || currentYear - this.year > 1){
          return null;
        }
      } else if(this.age == "bekas") {
        if(this.downPayment < 0.25*this.totalLoan){
          return null;
        }
      }
      return new Loan(this);
    }
  }
}
