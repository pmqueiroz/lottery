/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lottery;

import Screens.Index;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;

public class Lottery {
    
    public static ArrayList<Integer> lotoNumbers = new ArrayList();
    public static ArrayList<Long> validNumbers = new ArrayList();
    public static ArrayList<Integer> spotedNumbers = new ArrayList();
    public static String contestName = "";
    public static String nextJackPot = "";
    
    public static void addNumbers(int numbers){
        lotoNumbers.add(numbers);
        System.out.println(lotoNumbers);
        
    }
    
    public static String compare(){
        String message = "blo"; 
        int qtt = 0;
        for(int i = 0; i < 15; i++){
            if(validNumbers.contains(Long.valueOf(lotoNumbers.get(i)))){
               qtt++;
               spotedNumbers.add(lotoNumbers.get(i));
            } 
        }
        message = "Você acertou " + qtt + " dos 15 números";
        return message;
    }

    public static void main(String[] args) throws IOException {
      
      System.out.println("Aguarde processo em execusão...");  
      
      java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF); 
      java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);  
      
      CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
      String URL = "http://www.loterias.caixa.gov.br/wps/portal/loterias/landing/lotofacil";
      WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
      HtmlPage page = webClient.getPage(URL);
      webClient.getOptions().setJavaScriptEnabled(true);
      webClient.waitForBackgroundJavaScript(9000);
      
      System.out.println(page.getTitleText());
      System.out.println(page.getUrl().toString());
            
      List<HtmlElement> spans = page.getBody().getElementsByAttribute("span", "class", "ng-binding");
      HtmlSpan concurso = (HtmlSpan) spans.get(0);
      String ccs = concurso.getTextContent().replaceAll("\\s+", " ").trim();
      contestName = ccs;
      System.out.println(ccs);
      
      List<Long> resultados = new ArrayList();  
      List<HtmlElement> lista = page.getBody().getElementsByAttribute("table", "class", "simple-table lotofacil"); 
      HtmlTable tabela = (HtmlTable) lista.get(0);
      List<HtmlTableCell> cells = tabela.getBodies().get(0).getElementsByAttribute("td", "ng-repeat", "dezena in resultadoLinha");
      cells.forEach(cell -> resultados.add(Long.parseLong(cell.getTextContent())));
      System.out.println(resultados);
      
      validNumbers = (ArrayList<Long>)resultados;
      
      List<HtmlElement> division = page.getBody().getElementsByAttribute("div", "class", "resultado-loteria");
      List<HtmlElement> paragrafos = division.get(0).getElementsByAttribute("p", "class", "ng-binding");
      String data = paragrafos.get(0).getTextContent().trim();
      data = "Data do próximo sorteio: ".concat(data.substring((data.length() - 10), data.length()));
      nextJackPot = "".concat(data.substring((data.length() - 10), data.length()));
      System.out.println(data);
      

       new Index().setVisible(true);
       
    }
    
}
