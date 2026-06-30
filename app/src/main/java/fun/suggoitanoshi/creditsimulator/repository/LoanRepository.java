package fun.suggoitanoshi.creditsimulator.repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;

import fun.suggoitanoshi.creditsimulator.model.Loan;

public class LoanRepository{
  private HttpClient client;
  public LoanRepository(){
    client = HttpClient.newBuilder()
      .followRedirects(HttpClient.Redirect.NORMAL)
      .build();
  }

  public Loan GetLoanFrom(String url){
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(url))
      .header("Accept", "application/json")
      .GET()
      .build();
    try{
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      if(response.statusCode() != 200){
        return null;
      }
      Gson gson = new Gson();
      Loan loan = gson.fromJson(response.body(), Loan.class);
      return loan;
    } catch(IOException|InterruptedException e){
      return null;
    }
  }
}
