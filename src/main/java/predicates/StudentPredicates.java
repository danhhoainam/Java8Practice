package predicates;

import enums.Gender;
import models.Student;

import java.util.List;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author: Nichol
 * @date: {date: yyyy-MM-dd}
 * @description:
 */
public class StudentPredicates {

	public static Predicate<Student> isMaleStudent = student -> Gender.MALE.equals(student.getGender());

	public static Predicate<Student> isFemaleStudent = student -> Gender.FEMALE.equals(student.getGender());

	public static Predicate<Student> isStudentPassed = student -> student.getMarks() > 20;

	public static IntPredicate isLessThan30 = mark -> mark < 30;

	public static LongPredicate isCorrectId = id -> id > 0;

	public static DoublePredicate isCorrectFee = fee -> fee < 100;

	public static List<Student> filterStudent(Predicate<Student> predicate, List<Student> students) {
		return students.stream().filter(predicate).collect(Collectors.toList());
	}
}
