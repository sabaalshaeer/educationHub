package educationhub.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data // create getters , setters ,toString and HashCode and equals methods for us
@NoArgsConstructor // create constructor with out argument, if we dont have this constructor the
					// Jackson will fail when we try to convert the class into JSON and JSON into
					// class
@Entity // mark this class as a JPA Entity which represents table in a relational
		// database
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentId;
	private String studentFirstName;
	private String studentLastName;
	private int age;
	@Column(unique = true)
	private String studentEmail;
	private int grade;

	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "school_id", nullable = false)
	private School school;

	/*
	 * Many students can be associated with one school.
	 */

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "students")
	private Set<Teacher> teachers = new HashSet<>();

}
