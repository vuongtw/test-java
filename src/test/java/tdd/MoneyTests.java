package tdd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoneyTests {

  @Test
  @DisplayName("5 dollars times 2 equals 10 dollars")
  public void testMultiplication() {
    Money five = Money.dollar(5);
    assertEquals(Money.dollar(10), five.times(2));
    assertEquals(Money.dollar(15), five.times(3));
  }

  @Test
  @DisplayName("5 Francs times 2 equals 10 Francs")
  public void testFrancMultiplication() {
    Money five = Money.franc(5);
    assertEquals(Money.franc(10), five.times(2));
    assertEquals(Money.franc(15), five.times(3));
  }

  @Test
  @DisplayName("5 dollars must equal to 5 dollars")
  public void testEquality() {
    assertTrue(Money.dollar(5).equals(Money.dollar(5)));
    assertFalse(Money.dollar(5).equals(Money.dollar(6)));
    assertTrue(Money.franc(5).equals(Money.franc(5)));
  }

  @Test
  @DisplayName("Currency abbrv should be correct for every currencies")
  public void testCurrency() {
    assertEquals("USD", Money.dollar(1).currency());
    assertEquals("CHF", Money.franc(1).currency());
  }

  @Test
  @DisplayName("The sum of 2 5-dollar is 10-dollar")
  public void testSimpleAddition() {
    Money five = Money.dollar(5);
    Expression result = five.plus(five);
    Sum sum = (Sum) result;
    Bank bank = new Bank();
    Money reduced = bank.reduce(sum, "USD");
    assertEquals(Money.dollar(10), reduced);
  }

  @Test
  @DisplayName("The sum of 3-dollar and 4-dollar should be 7")
  public void testReduceSum() {
    Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
    Bank bank = new Bank();
    Money result = bank.reduce(sum, "USD");
    assertEquals(Money.dollar(7), result);
  }

  @Test
  @DisplayName("Test reduce money")
  public void testReduceMoney() {
    Bank bank = new Bank();
    Money result = bank.reduce(Money.dollar(1), "USD");
    assertEquals(Money.dollar(1), result);
  }

  @Test
  @DisplayName("Test reduce money of different currency")
  public void testReduceMoneyDifferentCurrency() {
    Bank bank = new Bank();
    bank.addRate("CHF", "USD", 2);
    Money result = bank.reduce(Money.franc(2), "USD");
    assertEquals(Money.dollar(1), result);
  }

  @Test
  @DisplayName("Test identity rate")
  public void testIdentityRate() {
    assertEquals(1, new Bank().rate("USD", "USD"));
  }

  @Test
  @DisplayName("Test mixed addition")
  public void testMixedAddition() {
    Expression fiveBucks = Money.dollar(5);
    Expression tenFrancs = Money.franc(10);
    Bank bank = new Bank();
    bank.addRate("CHF", "USD", 2);
    Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
    assertEquals(Money.dollar(10), result);
  }

  @Test
  @DisplayName("Test sum plus money")
  public void testSumPlusMoney() {
    Expression fiveBucks = Money.dollar(5);
    Expression tenFrancs = Money.franc(10);
    Bank bank = new Bank();
    bank.addRate("CHF", "USD", 2);
    Expression sum = new Sum(fiveBucks, tenFrancs).plus(fiveBucks);
    Money result = bank.reduce(sum, "USD");
    assertEquals(Money.dollar(15), result);
  }

  @Test
  @DisplayName("Test sum times")
  public void testSumTimes() {
    Expression fiveBucks = Money.dollar(5);
    Expression tenFrancs = Money.franc(10);
    Bank bank = new Bank();
    bank.addRate("CHF", "USD", 2);
    Expression sum = new Sum(fiveBucks, tenFrancs).times(2);
    Money result = bank.reduce(sum, "USD");
    assertEquals(Money.dollar(20), result);
  }
}
