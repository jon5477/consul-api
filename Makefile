ifdef MVN_FLAGS
    $(info MVN_FLAGS defined manually)
else
    MVN_FLAGS := "-e"
endif

.SILENT:

package:
	./mvnw $(MVN_FLAGS) -DskipTests package

test:
	./mvnw $(MVN_FLAGS) test
