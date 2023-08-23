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

@Data// create getters , setters ,toString and HashCode and equals methods for us
@NoArgsConstructor // create constructor with out argument, if we dont have this constructor the
					// Jackson will fail when we try to convert the class into JSON and JSON into
					// class
@Entity // mark this class as a JPA Entity which represents table in a relational
		// database
public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long teacherId;
	private String teacherFirstName;
	private String teacherLastName;
	@Column(unique = true)//when JPA create table , will create index on email so that will keeps email column as unique 
	private String teacherEmail;
	private String teacherSubject;
	
//	public String getTeacherEmail() {
//        return teacherEmail;
//    }

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "school_id", nullable = false)
	private School school;

	/*
	 * Many teachers can be associated with one school.
	 */

//	@ManyToMany(cascade = CascadeType.PERSIST)
//	@JoinTable(name = "teacher_student", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
//	@EqualsAndHashCode.Exclude
//	@ToString.Exclude
//	private Set<Student> students = new HashSet<>();
	
	
	@ManyToMany(mappedBy = "teachers")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Student> students = new HashSet<>();

}
