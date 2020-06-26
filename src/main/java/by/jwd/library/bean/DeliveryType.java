package by.jwd.library.bean;

import java.io.Serializable;
import java.util.Objects;

public class DeliveryType implements Serializable {
    private static final long serialVersionUID = 7005011906105342896L;

    private User user;
    private LoanType loanType;

    public DeliveryType(){
        setUser(new User());
        setLoanType(new LoanType());
    }

    public DeliveryType(User user, LoanType loanType){
        setUser(user);
        setLoanType(loanType);
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if ((obj.getClass() != this.getClass())) return false;
        DeliveryType that = (DeliveryType) obj;
        return Objects.equals(user, that.user) &&
                Objects.equals(loanType, that.loanType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, loanType);
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) +
                "DeliveryReservation{" +
                "user=" + user +
                ", loanType=" + loanType +
                '}';
    }
}
