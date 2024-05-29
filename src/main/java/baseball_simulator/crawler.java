package baseball_simulator;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileWriter;

import java.time.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class crawler {
	
    // Save File
    public static void saveCsv(String filename, String csv) {
        File savedir = new File("data");
        if (!savedir.exists()) {
            savedir.mkdirs();
        }

        File file = new File(savedir, filename);
        try (FileWriter out = new FileWriter(file)) {
            out.write(csv);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	
        Map<String, String> teamMap = new HashMap<>();
        teamMap.put("KIA", "HT");
        teamMap.put("LG", "LG");
        teamMap.put("두산", "OB");
        teamMap.put("삼성", "SS");
        teamMap.put("NC", "NC");
        teamMap.put("SSG", "SK");
        teamMap.put("KT", "KT");
        teamMap.put("한화", "HH");
        teamMap.put("키움", "WO");
        teamMap.put("롯데", "LT");
        
        String[] teams = {"KIA", "LG", "두산", "삼성", "NC", "SSG", "KT", "한화", "키움", "롯데"};

		//comment the above 2 lines and uncomment below 2 lines to use Chrome
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
        String baseUrl1 = "https://www.koreabaseball.com/Record/Player/HitterBasic/Basic1.aspx";
        String baseUrl2 = "https://www.koreabaseball.com/Record/Player/HitterBasic/Basic2.aspx";

        try {
            // 팀 이름 배열을 반복하며 각 팀을 선택
            for (String team : teams) {
                String value = teamMap.get(team);
                
                // 크롤링할 웹 페이지로 이동
                driver.get(baseUrl1); // 실제 URL로 변경
                
                // 페이지가 로드될 때까지 대기 (필요에 따라 조정)
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                
                if (value != null) {
                    // 각 반복마다 select 요소를 다시 찾기
                    WebElement teamSelectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cphContents_cphContents_cphContents_ddlTeam_ddlTeam")));
                    Select teamSelect = new Select(teamSelectElement);

                    // 팀 선택
                    teamSelect.selectByValue(value);
                    System.out.println("선택된 팀: " + team);

                    // 선택이 반영될 시간을 대기
                    Thread.sleep(2000); // 2초 대기

                 // 데이터 테이블 찾기
                    WebElement table1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.tData01.tt")));
                    List<WebElement> rows1 = table1.findElements(By.tagName("tr"));

                    StringBuilder sb1 = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();
                    sb1.append("선수명,안타,2루타,3루타,홈런\n");
                    sb2.append("선수명,볼넷,사구,출루율\n");

                    for (WebElement row : rows1) {
                        List<WebElement> cols = row.findElements(By.tagName("td"));
                        if (cols.size() > 0) {
                            String playerName = cols.get(1).getText();  // 선수명
                            String hits = cols.get(8).getText();       // 안타
                            String doubles = cols.get(9).getText();    // 2루타
                            String triples = cols.get(10).getText();   // 3루타
                            String homeRuns = cols.get(11).getText();  // 홈런

                            // 기본1 페이지 데이터 수집
                            sb1.append(playerName).append(",");
                            sb1.append(hits).append(",");
                            sb1.append(doubles).append(",");
                            sb1.append(triples).append(",");
                            sb1.append(homeRuns).append("\n");
                        }
                    }

                    // Basic2 페이지로 이동하여 추가 데이터 추출
                    driver.get(baseUrl2);

                    // 선택이 반영될 시간을 대기
                    Thread.sleep(2000); // 2초 대기

                    // 팀 선택
                    WebElement teamSelectElement2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cphContents_cphContents_cphContents_ddlTeam_ddlTeam")));
                    Select teamSelect2 = new Select(teamSelectElement2);
                    teamSelect2.selectByValue(value);

                    // 선택이 반영될 시간을 대기
                    Thread.sleep(2000); // 2초 대기

                    // 데이터 테이블 찾기
                    WebElement table2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.tData01.tt")));
                    List<WebElement> rows2 = table2.findElements(By.tagName("tr"));
                    
                    for (WebElement row2 : rows2) {
                        List<WebElement> cols2 = row2.findElements(By.tagName("td"));
                        if (cols2.size() > 0) {
                            String playerName = cols2.get(1).getText();  // 선수명
                            String walks = cols2.get(4).getText();       // 볼넷
                            String hbp = cols2.get(6).getText();         // 사구
                            String obp = cols2.get(10).getText();        // 출루율

                            // Basic2 페이지 데이터 수집
                            sb2.append(playerName).append(",");
                            sb2.append(walks).append(",");
                            sb2.append(hbp).append(",");
                            sb2.append(obp).append("\n");
                        }
                    }
                    
                    // sb1과 sb2를 병합해서 sb 생성
                    StringBuilder sb = new StringBuilder();
                    sb.append("선수명,안타,2루타,3루타,홈런,볼넷,사구,출루율\n");

                    String[] sb1Lines = sb1.toString().split("\n");
                    String[] sb2Lines = sb2.toString().split("\n");
                   
                    for (int i = 1; i < sb1Lines.length; i++) {
                        String[] sb1Fields = sb1Lines[i].split(",");
                        String[] sb2Fields = sb2Lines[i].split(",");

                        // 선수명
                        sb.append(sb1Fields[0]).append(",");

                        // sb1의 안타, 2루타, 3루타, 홈런
                        for (int j = 1; j <= 4; j++) {
                            sb.append(sb1Fields[j]).append(",");
                        }

                        // sb2의 볼넷, 사구, 출루율
                        for (int j = 1; j < Math.min(sb2Fields.length, 4); j++) {
                            sb.append(sb2Fields[j]).append(",");
                        }

                        sb.append("\n");
                    }
                    
                    // CSV 파일로 저장
                    saveCsv(team + ".csv", sb.toString());
                } else {
                    System.out.println("팀 값을 찾을 수 없습니다: " + team);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.close();
    }
}
		
