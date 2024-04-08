package com.management.vehicle.leave;

public class LeaveRequest {
    // ngày xin nghi ko đc phép nhỏ hơn ngày hiện tại -> update bên controller duyệt ngày xin nghỉ
    // trên thời gian thực check ngày tháng hiện tại -> xem quá issueddate chưa
    private String issueDate;
    private String returnDate;
    private String reason;
    private RequestLeaveStatus leaveStatus;
    public LeaveRequest()
    {
        issueDate = "";
        reason = "";
        returnDate = "";
        leaveStatus = RequestLeaveStatus.NONE;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RequestLeaveStatus getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(RequestLeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
    }
}
