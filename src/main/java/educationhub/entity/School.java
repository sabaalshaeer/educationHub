package educationhub.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class School {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long schoolId;
	private String schoolName;
	private String schoolAddress;
	private String schoolCity;
	private String schoolState;
	private String schoolZip;
	private String schoolPhone;

	@OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)//mapped to field in student object which is school, that tell JPA that relationship is managed by that field
	@EqualsAndHashCode.Exclude//to prevent recursion  when Jackson convert the class to JSON
	@ToString.Exclude//to prevent recursion  when Jackson convert the class to JSON
	private Set<Teacher> teachers = new HashSet<>();

	@OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)//mapped to field in student object which is school, that tell JPA that relationship is managed by that field
	@EqualsAndHashCode.Exclude//to prevent recursion  when Jackson convert the class to JSON
	@ToString.Exclude//to prevent recursion  when Jackson convert the class to JSON
	private Set<Student> students = new HashSet<>();

}
