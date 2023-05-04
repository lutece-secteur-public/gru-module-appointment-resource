package fr.paris.lutece.plugins.appointment.modules.resource.business.form;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalTime;

/**
 * 
 * Calendar used to represent the earliest possible time, latest possible time,
 * and duration for an appointment in a given form
 *
 */
public class FormResourceCalendar implements Serializable {

	private static final long serialVersionUID = -3019091766087633610L;

	/**
	 * ID of the form being used
	 */
	private int _nIdForm;

	/**
	 * Starting time of the first possible appointment
	 */
	private LocalTime _startingTime;

	/**
	 * Ending time of the last possible appointment
	 */
	private LocalTime _endingTime;

	/**
	 * Duration of an appointment
	 */
	private int _nDuration;

	/**
	 * Default empty constructor
	 */
	public FormResourceCalendar( )
	{
		
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param idForm
	 * 			The Form's ID
	 * @param startingTime
	 * 			Earliest starting time for an appointment
	 * @param endingTime
	 * 			Latest ending time for an appointment
	 * @param duration
	 * 			Duration of an appointment
	 */
	public FormResourceCalendar(int idForm, LocalTime startingTime, LocalTime endingTime, int duration) {
		super();
		this._nIdForm = idForm;
		this._startingTime = startingTime;
		this._endingTime = endingTime;
		this._nDuration = duration;
	}

	public int getIdForm() {
		return _nIdForm;
	}

	public void setIdForm(int idForm) {
		this._nIdForm = idForm;
	}

	public LocalTime getStartingTime() {
		return _startingTime;
	}

	public void setStartingTime( Timestamp startingTime ) {
		this._startingTime = startingTime.toLocalDateTime().toLocalTime();
	}

	public LocalTime getEndingTime() {
		return _endingTime;
	}

	public void setEndingTime(Timestamp endingTime) {
		this._endingTime = endingTime.toLocalDateTime().toLocalTime();
	}

	public int getDuration() {
		return _nDuration;
	}

	public void setDuration(int duration) {
		this._nDuration = duration;
	}

	public static long getSerialversionUid() {
		return serialVersionUID;
	}
}
