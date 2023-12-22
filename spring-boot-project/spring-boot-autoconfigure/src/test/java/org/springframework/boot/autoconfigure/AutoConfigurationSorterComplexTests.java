/*
 * Copyright 2012-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurationSorterTests.SkipCycleMetadataReaderFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class AutoConfigurationSorterComplexTests {

	private static final String A = AutoConfigureA.class.getName();

	private static final String A1 = AutoConfigureA_SeedR_1.class.getName();

	private static final String A2 = AutoConfigureA_SeedY_2.class.getName();

	private static final String A3 = AutoConfigureA_SeedA_3.class.getName();

	private static final String A4 = AutoConfigureA_SeedG_4.class.getName();

	private AutoConfigurationSorter sorter;

	private AutoConfigurationMetadata autoConfigurationMetadata = mock(AutoConfigurationMetadata.class);

	@BeforeEach
	void setup() {
		this.sorter = new AutoConfigurationSorter(new SkipCycleMetadataReaderFactory(), this.autoConfigurationMetadata);
	}

	@Test
	void byBeforeAnnotationThenOrderAnnotation() {
		List<String> actual = this.sorter.getInPriorityOrder(Arrays.asList(A4, A3, A2, A1, A));
		assertThat(actual).containsExactly(A1, A2, A3, A4, A);
	}

	static class AutoConfigureA {
		
	}

	// Use seeds in autoconfiguration class names
	// to mislead the sort by names done in AutoConfigurationSorter class.
	@AutoConfigureBefore(AutoConfigureA.class)
	@AutoConfigureOrder(1)
	static class AutoConfigureA_SeedR_1 {
		
	}

	@AutoConfigureBefore(AutoConfigureA.class)
	@AutoConfigureOrder(2)
	static class AutoConfigureA_SeedY_2 {

	}

	@AutoConfigureBefore(AutoConfigureA.class)
	@AutoConfigureOrder(3)
	static class AutoConfigureA_SeedA_3 {

	}

	@AutoConfigureBefore(AutoConfigureA.class)
	@AutoConfigureOrder(4)
	static class AutoConfigureA_SeedG_4 {

	}

}