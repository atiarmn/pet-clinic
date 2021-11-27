package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.*;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(ReportingExtension.class)
class TriCongruenceTest {

	private static final Logger log = LoggerFactory.getLogger(TriCongruenceTest.class);

	@Test
	public void sampleTest() {
		Triangle t1 = new Triangle(2, 3, 7);
		Triangle t2 = new Triangle(7, 2, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}
	/*CUTPNFP tests for line 14:
	 * f = (t1[0] != t2[0]) || (t1[1] != t2[1]) || (t1[2] != t2[2])
	 * we can write f in regex: f = a + b + c which a,b,c are implicant, f is predicate
	 * there is 3 unique true points: TFF , FTF , FFT
	 * and there is 1 near false point : FFF
	 */
	/* first unique true point : TFF */
	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "a",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		})
	@Test
	void testAreCongruentWithNotEqualFirstEdgesOfTrianglesAndEqualOtherEdges() {
		Triangle triangle1 = new Triangle(3, 3, 1);
		Triangle triangle2 = new Triangle(1, 3, 1);
		boolean areCongruent = TriCongruence.areCongruent(triangle1, triangle2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}
	/* second unique true point : FTF */
	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = false)
		})
	@Test
	void testAreCongruentWithNotEqualSecondEdgesOfTrianglesAndEqualOtherEdges() {
		Triangle triangle1 = new Triangle(3, 3, 1);
		Triangle triangle2 = new Triangle(3, 1, 1);
		boolean areCongruent = TriCongruence.areCongruent(triangle1, triangle2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}
	/* third unique true point : FFT */
	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "c",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = true)
		}
	)
	@Test
	public void testAreCongruentWithNotEqualThirdEdgesOfTrianglesAndEqualOtherEdges(){
		Triangle t1 = new Triangle(3, 3, 1);
		Triangle t2 = new Triangle(3, 3, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	/* one test for near false point is enough because there is no difference in the implicants in FFF */
	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "a",
		clause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void testAreCongruentWithEqualEdges(){
		Triangle t1 = new Triangle(3, 3, 1);
		Triangle t2 = new Triangle(3, 3, 1);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);
	}

	/*CC tests for line 15:
	 * f = (t1[0] < 0) || (t1[0] + t1[1] < t1[2])
	 * we can write f in regex: f = x + y which x and y are clauses, f is predicate
	 * here we should test all the values that the clauses will get: x = true, x = false, y = true, y = false
	 * we can write two tests to check all these values: TT, FF
	 * these two test are enough because we will test every values that each clause can get.
	 */
	/* first CC : TT */
	@ClauseCoverage(
		predicate = "x + y",
		valuations = {
			@Valuation(clause = 'x', valuation = true),
			@Valuation(clause = 'y', valuation = true)
		}
	)
	@Test
	public void testAreCongruentWithFirstEdgeOfFirstTriangleLessThanZeroAndSumOfTwoFirstEdgesIsLessThanThirdEdge() {
		Triangle t1 = new Triangle(-3, 3, 1);
		Triangle t2 = new Triangle(-3, 3, 1);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);
	}
	/* second CC : FF */
	@ClauseCoverage(
		predicate = "x + y",
		valuations = {
			@Valuation(clause = 'x', valuation = false),
			@Valuation(clause = 'y', valuation = false)
		}
	)
	@Test
	public void testAreCongruentWithFirstEdgeOfFirstTriangleMoreThanZeroAndSumOfTwoFirstEdgesIsMoreThanThirdEdge() {
		Triangle t1 = new Triangle(3, 3, 1);
		Triangle t2 = new Triangle(3, 3, 1);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);
	}
	/*CACC tests for line 15:
	 * f = (t1[0] < 0) || (t1[0] + t1[1] < t1[2])
	 * we can write f in regex: f = x + y which x and y are clauses, f is predicate
	 * here we should decide one of the clauses as the major clause
	 * if x is the major clause: CACC will be : TF,FF which we know that TF is infeasible because the triangle arrays are sorted
	 * if y is the major clause: CACC will be : FT,FF
	 * so we choose y as the major clause
	 */
	/* first CACC : FT */
	@CACC(
		predicate = "x + y",
		valuations = {
			@Valuation(clause = 'x', valuation = false),
			@Valuation(clause = 'y', valuation = true)
		},
		majorClause = 'y',
		predicateValue = true
	)
	@Test
	public void testAreCongruentWithFirstEdgeOfFirstTriangleMoreThanZeroAndSumOfTwoFirstEdgesIsLessThanThirdEdge() {
		Triangle t1 = new Triangle(3, 1, 5);
		Triangle t2 = new Triangle(3, 1, 5);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}
	/* first CACC : FF */
	@CACC(
		predicate = "x + y",
		valuations = {
			@Valuation(clause = 'x', valuation = false),
			@Valuation(clause = 'y', valuation = false)
		},
		majorClause = 'y',
		predicateValue = false
	)
	@Test
	public void testAreCongruentWithFirstEdgeOfFirstTriangleMoreThanZeroAndSumOfTwoFirstEdgesIsMoreThanThirdEdge() {
		Triangle t1 = new Triangle(3, 3, 1);
		Triangle t2 = new Triangle(3, 3, 1);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);
	}

	/* to prove that CUTPNFP doesn't subsume UTPC we can use an example:
	 * f = ab + cd
	 * Implicant ab has 3 unique true points : {TTFF, TTFT, TTTF}
			• For clause a, we can pair unique true point TTFF with near false point FTFF
			• For clause b, we can pair unique true point TTFF with near false point TFFF
	 * Implicant cd has 3 unique true points : {FFTT, FTTT, TFTT}
			• For clause c, we can pair unique true point FFTT with near false point FFFT
			• For clause d, we can pair unique true point FFTT with near false point FFTF
	 * CUTPNFP set : {TTFF, FFTT, TFFF, FTFF, FFTF, FFFT}
	 * for UTPC: Given minimal DNF representations of a predicate f and its negation ~f , TR contains a unique true point for each implicant in f and each implicant in ~f
	 * ~f = ~a~c + ~a~d + ~b~c + ~b~d
	 * ab:{TTFF, TTFT, TTTF}
	 * cd:{FFTT, FTTT, TFTT}
	 * ~a~c:{FTFT}
	 * ~a~d:{FTTF}
	 * ~b~c:{TFFT}
	 * ~b~d:{TFTF}
	 * UTPC set : {TTFF, TTFT, TTTF, FFTT, FTTT, TFTT, FTFT, FTTF, TFFT, TFTF}
	 * as you can see CUTPNFP doesn't subsume UTPC.
	 */
	private static boolean question2(boolean a, boolean b, boolean c, boolean d) {
		boolean predicate = false;
		predicate = (a && b) || (c && d);
		return predicate;
	}
}
