package br.org.top5.model;

import static javax.persistence.TemporalType.DATE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

import lombok.Data;

@Data
@Entity
@Table(name = "VOTE")
public class Vote {	

	@Id
	@Column(name = "id_vote")
	@TableGenerator(name = "VOTE_GEN", allocationSize = 1, initialValue = 1, table = "T_ID", pkColumnName = "key", valueColumnName = "valor", pkColumnValue = "ID_VOTE")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "VOTE_GEN")
	private long id;
	
	@Temporal(DATE)
	private Date voteDate;
	
	
	@OneToOne
	@JoinColumn(name = "id_movie", nullable = false)
	private Movie movie;
	
	@OneToOne
	@JoinColumn(name = "id_user", nullable = false)
	private User user;

}
