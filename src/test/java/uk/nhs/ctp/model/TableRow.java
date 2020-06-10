package uk.nhs.ctp.model;

import java.util.Iterator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TableRow {

  private Iterator<WebElement> iterator;

  public TableRow(WebElement row) {
    this.iterator = row.findElements(By.tagName("td")).iterator();
  }

  public String nextCell() {
    String text = iterator.next().getText();
    return text != null && text.trim().isEmpty() ? null : text;
  }

}
