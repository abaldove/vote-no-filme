package br.org.top5.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "USERS")
public class User {

	@Id
	@Column(name = "id_user")
	@TableGenerator(name = "USER_GEN", allocationSize = 1, initialValue = 1, table = "T_ID", pkColumnName = "key", valueColumnName = "valor", pkColumnValue = "ID_USER")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "USER_GEN")
	private long id;

	@Column
	private String email;

	@Column
	private String login;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "user")
	private List<Vote> votes;

	public Map<String, Integer> getUserRanking() {
		Map<String, Integer> ranking = new HashMap<String, Integer>();
		for (Vote vote : votes) {
			String name = vote.getMovie().getName();
			Integer movieVotes = ranking.get(name);
			if (null != movieVotes) {
				movieVotes++;
				ranking.put(name, movieVotes);
			} else {
				ranking.put(name, 1);
			}
		}
		return ranking;
	}

}
