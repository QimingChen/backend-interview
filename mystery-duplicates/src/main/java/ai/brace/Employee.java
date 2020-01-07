package ai.brace;

public class Employee {

  public String firstName;
  public String middleInitial;
  public String lastName;
  public String socialSecurityNumber;

  public Employee(String _lastName, String _firstName, String _middleInitial,
      String _socialSecurityNumber) {
    firstName = _firstName;
    middleInitial = _middleInitial;
    lastName = _lastName;
    socialSecurityNumber = _socialSecurityNumber;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  public boolean equals(Object o) {
    return o instanceof Employee && this.hashCode() == o.hashCode() ? true : false;
  }

  public String toString() {
    return firstName + middleInitial + lastName + socialSecurityNumber;
  }
}
