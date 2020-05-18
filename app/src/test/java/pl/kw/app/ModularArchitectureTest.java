package pl.kw.app;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import java.util.Set;

@AnalyzeClasses(packages = "pl.bnp.retail.okd.ho.app")
public class ModularArchitectureTest {
    private static final String ACCOUNT_CORE = "..account.core..";
    private static final Set<String> CORES =
        Set.of(ACCOUNT_CORE);

    private static String[] getAllCoresBut(String one) {
        return CORES.stream().filter(o -> !o.equals(one)).toArray(String[]::new);
    }

    private static ArchRule packageShouldNotDependOnAnyPackage(String one, String... other) {
        return noClasses()
            .that()
            .resideInAPackage(one)
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(other);
    }

    @ArchTest
    public static final ArchRule lead_core_should_depend_on_no_other_cores =
        packageShouldNotDependOnAnyPackage(ACCOUNT_CORE, getAllCoresBut(ACCOUNT_CORE));
}
