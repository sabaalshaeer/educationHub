package educationhub.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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


	@JoinColumn(name = "school_id", nullable = false)
	@ToString.Exclude
	@ManyToOne
	private School school;

	/*
	 * Many students can be associated with one school.
	 */


//	@ManyToMany(mappedBy = "students",cascade = CascadeType.PERSIST)
//	@EqualsAndHashCode.Exclude
//	@ToString.Exclude
//	private Set<Teacher> teachers = new HashSet<>();
	
	
	//FK and Create the join table
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany
	@JoinTable(name = "teacher_student", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"))
	private Set<Teacher> teachers = new HashSet<>();

}
