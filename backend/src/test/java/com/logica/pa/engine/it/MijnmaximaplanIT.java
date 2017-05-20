package com.logica.pa.engine.it;

import java.io.File;

import org.junit.runner.RunWith;

import com.logica.pa.engine.scenario.ScenarioRunner;
import com.logica.pa.engine.scenario.SpadeScenarioTest;
import com.logica.pa.engine.utils.AetherUtils;

/**
 * Scenario test for mijnmaximaplan.
 */
@RunWith(ScenarioRunner.class)
@SpadeScenarioTest(name = "mijnmaximaplan", dataPatterns = "data/**/*.csv")
public class MijnmaximaplanIT {
	static final String[] COORDS = AetherUtils.getMavenCoordinates();
	static final String VERSION = COORDS[2]; // e.g. "1.3.21"
	static final String MODULE_ARTIFACT_ID = "mijnmaximaplan";
	static final String DATA_ARTIFACT_ID = "mijnmaximaplan";
	static final String GROUP_ID = COORDS[0];
	
	public static File getModuleFile() {
		return new File("target", MODULE_ARTIFACT_ID + "-" + VERSION + ".jar");
		//return AetherUtils.getModuleArtifact(GROUP_ID, MODULE_ARTIFACT_ID, VERSION);
	}
}
