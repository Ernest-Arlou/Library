package by.jwd.library.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class LoanType implements Serializable {
    private static final long serialVersionUID = 4964287832198817363L;

    private int userId;
    private int loanTypeId;
    private int copyId;
    private int duration;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private MediaDetail mediaDetail;

    public LoanType() {
        userId = 1;
        loanTypeId = 1;
        copyId = 1;
        duration = 1;

        startDate = LocalDate.now();
        endDate = LocalDate.now();

        status = "NoStatus";

        mediaDetail = new MediaDetail();

    }

    public LoanType(int userId, int loanTypeId, int copyId, int duration, LocalDate startDate, LocalDate endDate, String status, MediaDetail mediaDetail) {
        this.userId = userId;
        this.loanTypeId = loanTypeId;
        this.copyId = copyId;
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mediaDetail = mediaDetail;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCopyId() {
        return copyId;
    }

    public void setCopyId(int copyId) {
        this.copyId = copyId;
    }

    public MediaDetail getMediaDetail() {
        return mediaDetail;
    }

    public void setMediaDetail(MediaDetail mediaDetail) {
        this.mediaDetail = mediaDetail;
    }

    public int getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(int loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if ((obj.getClass() != this.getClass())) return false;
        LoanType loanType = (LoanType) obj;
        return userId == loanType.userId &&
                loanTypeId == loanType.loanTypeId &&
                copyId == loanType.copyId &&
                duration == loanType.duration &&
                Objects.equals(startDate, loanType.startDate) &&
                Objects.equals(endDate, loanType.endDate) &&
                Objects.equals(status, loanType.status) &&
                Objects.equals(mediaDetail, loanType.mediaDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, loanTypeId, copyId, duration, startDate, endDate, status, mediaDetail);
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) +
                "LoanType{" +
                "userId=" + userId +
                ", loanTypeId=" + loanTypeId +
                ", copyId=" + copyId +
                ", duration=" + duration +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", mediaDetail=" + mediaDetail +
                '}';
    }
}
