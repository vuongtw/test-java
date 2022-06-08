package tdd;
import java.util.Hashtable;

interface Expression {
  Money reduce(Bank bank, String to);
  Expression plus(Expression addend);
  Expression times(int multiplier);
}

class Money implements Expression {
  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  String currency() {
    return currency;
  }

  public String toString() {
    return amount + " " + currency;
  }

  @Override
  public boolean equals(Object object) {
    Money money = (Money) object;
    return amount == money.amount &&
      currency().equals(money.currency());
  }

  static Money dollar(int amount) {
    return new Money(amount, "USD");
  }

  static Money franc(int amount) {
    return new Money(amount, "CHF");
  }

  public Expression times(int multiplier) {
    return new Money(amount * multiplier, currency);
  }

  public Expression plus(Expression addend) {
    return new Sum(this, addend);
  }

  public Money reduce(Bank bank, String to) {
    int rate = bank.rate(currency, to);
    return new Money(amount / rate, to);
  }
}

class Sum implements Expression {
  Expression augend;
  Expression addend;

  Sum(Expression augend, Expression addend) {
    this.augend = augend;
    this.addend = addend;
  }

  public Money reduce(Bank bank, String to) {
    int amount = augend.reduce(bank, to).amount + addend.reduce(bank, to).amount;
    return new Money(amount, to);
  }

  public Expression plus(Expression addend) {
    return new Sum(this, addend);
  }

  public Expression times(int multiplier) {
    return new Sum(augend.times(multiplier), addend.times(multiplier));
  }
}

class Bank {
  private Hashtable<Pair, Integer> rates = new Hashtable<Pair, Integer>();

  public void addRate(String from, String to, int rate) {
    rates.put(new Pair(from, to), Integer.valueOf(rate));
  }

  int rate(String from, String to) {
    if (from.equals(to)) return 1;
    Integer rate = (Integer) rates.get(new Pair(from, to));
    return rate.intValue();
  }

  Money reduce(Expression source, String to) {
    return source.reduce(this, to);
  }
}

class Pair {
  private String from;
  private String to;

  Pair(String from, String to) {
    this.from = from;
    this.to = to;
  }

  public boolean equals(Object object) {
    Pair pair = (Pair) object;
    return from.equals(pair.from) && to.equals(pair.to);
  }

  public int hashCode() {
    return 0;
  }
}
