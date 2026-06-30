package fun.suggoitanoshi.creditsimulator.view;

import java.util.List;

import fun.suggoitanoshi.creditsimulator.model.Loan;

public class CLI {
  public void RenderMenu(){
    System.out.println("Credit Simulator");
    System.out.println("mulai: mulai input simulasi kredit");
    System.out.println("menu: menu ini");
    System.out.println("status: tampilkan status kredit");
    System.out.println("hitung: tampilkan perhitungan");
    System.out.println("load: ambil data dari API");
    System.out.println("exit: keluar dari credit simulator");
  }

  public void RenderPromptLeader(){
    System.out.println("---Input Simulasi Kredit---");
    System.out.println("batal: batalkan simulasi kredit");
  }

  public void RenderPromptType(){
    System.out.println("jenis kendaraan [mobil/motor]: ");
  }

  public void RenderUnknownType(){
    System.out.println("jenis kendaraan tidak dikenali");
  }

  public void RenderPromptAge(){
    System.out.println("kondisi kendaraan [bekas/baru]: ");
  }

  public void RenderUnknownAge(){
    System.out.println("kondisi kendaraan tidak dikenali");
  }

  public void RenderPromptYear(){
    System.out.println("tahun kendaraan [0-9999]: ");
  }

  public void RenderInvalidYear(){
    System.out.println("tahun kendaraan tidak valid!");
  }

  public void RenderPromptLoan(){
    System.out.println("jumlah pinjaman [0-1000000000]: ");
  }

  public void RenderInvalidLoan(){
    System.out.println("jumlah pinjaman tidak valid!");
  }

  public void RenderPromptTenor(){
    System.out.println("tenor pinjaman dalam tahun [1-6]: ");
  }

  public void RenderInvalidTenor(){
    System.out.println("tenor pinjaman tidak valid!");
  }

  public void RenderPromptDownPayment(){
    System.out.println("Jumlah DP: ");
  }

  public void RenderInvalidDownPayment(){
    System.out.println("Jumlah DP tidak valid!");
  }

  public void RenderUnknownError(){
    System.out.println("error, kembali ke menu awal.");
  }

  public void RenderCalculation(Loan loan){
    List<Double> interestPerYear = loan.getInterestPerYear();
    List<Double> totalPaymentPerYear = loan.getTotalPaymentPerYear();

    System.out.println("Hasil perhitungan kredit:");

    for(int i = 0; i < loan.getTenor(); i++){
      System.out.printf("Tahun %d: Rp.%.2f, Rate: %.2f%%\n", i+1, totalPaymentPerYear.get(i), interestPerYear.get(i));
    }
  }

  public void RenderLoan(Loan loan){
    System.out.printf("Tipe kendaraan: %s\n", loan.getType());
    System.out.printf("Status kendaraan: %s\n", loan.getAge());
    System.out.printf("Tahun kendaraan: %d\n", loan.getYear());
    System.out.printf("Tenor: %d\n", loan.getTenor());
    System.out.printf("Jumlah pinjaman: %d\n", loan.getTotalLoan());
    System.out.printf("Jumlah DP: %d\n", loan.getDownPayment());
  }

  public void RenderNoValidLoan(){
    System.out.println("Kredit tidak valid!");
  }

  public void RenderExit(){
    System.out.println("Exiting...");
    System.out.println("Goodbye!");
  }
}
