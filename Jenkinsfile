pipeline {
	agent any

	tools {
		maven 'Maven-3.9'
	}

	parameters {
		choice(name: 'SUITE_FILE',
			choices: ['testng-ui.xml', 'testng-api.xml', 'testng-db.xml'],
			description: 'Select TestNG suite XML')

		string(name: 'GROUPS', defaultValue: '', description: 'Run specific TestNG groups')
		string(name: 'TEST_CLASS', defaultValue: '', description: 'Run a single test class')
		string(name: 'TEST_METHOD', defaultValue: '', description: 'Run a single test method')

		choice(name: 'BROWSER',
			choices: ['chromium', 'webkit'],
			description: 'Override browser')

		string(name: 'THREAD_COUNT', defaultValue: '', description: 'Number of threads')
	}

	environment {
		REPORT_DIR = "reports/extentReports/latest"
	}

	stages {

		stage('Checkout') {
			steps {
				git branch: 'main',
				url: 'https://github.com/ne20641570/Playwrite_Project_Bank.git',
				credentialsId: 'github-creds'
			}
		}

		stage('Build') {
			steps {
				sh 'mvn clean compile'
			}
		}

		stage('Run Tests') {
			steps {

				// -------- RUN TESTS --------
				script {
					def mvnCmd = "mvn clean test -Dsurefire.suiteXmlFiles=${params.SUITE_FILE}"

					if (params.GROUPS?.trim()) {
						mvnCmd += " -Dgroups=${params.GROUPS}"
					}
					if (params.TEST_CLASS?.trim()) {
						mvnCmd += " -Dtest=${params.TEST_CLASS}"
					}
					if (params.TEST_METHOD?.trim()) {
						mvnCmd += " -Dtest=${params.TEST_METHOD}"
					}
					if (params.BROWSER?.trim()) {
						mvnCmd += " -Dbrowser=${params.BROWSER}"
					}
					if (params.THREAD_COUNT?.trim()) {
						mvnCmd += " -Dthread.count=${params.THREAD_COUNT}"
					}

					echo "Running: ${mvnCmd}"

					catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
						sh mvnCmd
					}
				}

				// -------- REPORT & VIDEO HANDLING --------
				script {
					def reportFile = ""
					if (params.SUITE_FILE == 'testng-ui.xml') {
						reportFile = "Automation Playwright Suite.html"
					} else if (params.SUITE_FILE == 'testng-api.xml') {
						reportFile = "API RestAssured Suite.html"
					} else if (params.SUITE_FILE == 'testng-db.xml') {
						reportFile = "DataBase Suite.html"
					}

					def today = new Date().format('yyyy-MM-dd')
					def videoSourceDir = "reports/videos/${today}/video"
					def videoTargetDir = "${env.REPORT_DIR}/videos"

					sh """
                        echo "Preparing Extent Report..."
                        mkdir -p ${env.REPORT_DIR}
                        find reports -name "${reportFile}" -exec cp {} ${env.REPORT_DIR}/index.html \\; || true

                        echo "Copying failure videos..."
                        if [ -d "${videoSourceDir}" ]; then
                            mkdir -p ${videoTargetDir}
                            cp -R ${videoSourceDir}/* ${videoTargetDir}/ || true
                        else
                            echo "No videos found"
                        fi
                    """
				}
			}
		}
	}

	post {
		always {

			// -------- PUBLISH HTML REPORT --------
			publishHTML(target: [
				reportName: "ExtentReport",   // ⚠️ no spaces = clean URL
				reportDir: "${env.REPORT_DIR}",
				reportFiles: "index.html",
				keepAll: true,
				alwaysLinkToLastBuild: true,
				allowMissing: false
			])

			// -------- ARCHIVE REPORT & VIDEOS --------
			archiveArtifacts artifacts: """
                ${env.REPORT_DIR}/index.html,
                ${env.REPORT_DIR}/videos/**/*
            """, allowEmptyArchive: true

			// -------- BUILD SUMMARY LINKS (FIXED) --------
			script {
				def reportUrl = "${env.BUILD_URL}ExtentReport/"
				def videoUrl  = "${env.BUILD_URL}artifact/${env.REPORT_DIR}/videos/"

				currentBuild.description = """
                <b>Extent Report:</b>
                <a href='${reportUrl}' target='_blank'>Open Report</a><br/>
                <b>Failure Videos:</b>
                <a href='${videoUrl}' target='_blank'>Open Videos</a>
                """
			}
		}
	}
}
