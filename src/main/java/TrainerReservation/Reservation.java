package TrainerReservation;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Reservation_table")
public class Reservation {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long reservationId;
    private Long trainerId;
    private String userName;
    private String reservationDate;
    private String status;

    @PostPersist
    public void onPostPersist(){
        ReservationRequested reservationRequested = new ReservationRequested();

        reservationRequested.setId(this.getReservationId());
        reservationRequested.setReservationId(this.getReservationId());
        reservationRequested.setTrainerId(this.getTrainerId());
        reservationRequested.setReservationDate(this.getReservationDate());
        reservationRequested.setUserName(this.getUserName());

        BeanUtils.copyProperties(this, reservationRequested);
        reservationRequested.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate(){

        if(this.getStatus().equals("Canceled")) {
            ReservationCanceled reservationCanceled = new ReservationCanceled();
            BeanUtils.copyProperties(this, reservationCanceled);
            reservationCanceled.publishAfterCommit();
        }
        else if(this.getStatus().equals("Confirmed"))
        {
            ReservationConfirmed reservationConfirmed = new ReservationConfirmed();
            BeanUtils.copyProperties(this, reservationConfirmed);
            reservationConfirmed.publishAfterCommit();
        }

    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
