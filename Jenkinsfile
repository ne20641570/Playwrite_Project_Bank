pipeline {
	agent any

	parameters {
		string(name: 'BRANCH', defaultValue: 'main', description: 'Git branch to build')
		string(name: 'TEST_SUITE', defaultValue: 'ui', description: 'Test suite to run (ui/db/api)')
	}

	environment {
		// Set Java Home (adjust if using a different JDK)
		JAVA_HOME = "/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home"
	}

	stages {

		stage('Setup Maven') {
			steps {
				echo 'Checking Maven installation...'
				script {
					// Check if mvn exists
					def mvnExists = sh(script: 'which mvn || true', returnStdout: true).trim()
					if (!mvnExists) {
						echo 'Maven not found. Installing via Homebrew...'
						sh '''
                            # Install Homebrew if not installed
                            if ! command -v brew >/dev/null 2>&1; then
                                /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
                            fi
                            # Install Maven
                            brew update
                            brew install maven
                        '''
					} else {
						echo "Maven found at ${mvnExists}"
					}
				}
			}
		}

		stage('Checkout SCM') {
			steps {
				echo "Checking out branch: ${params.BRANCH}"
				checkout([$class: 'GitSCM',
					branches: [[name: "*/${params.BRANCH}"]],
					userRemoteConfigs: [[url: 'https://github.com/ne20641570/Playwrite_Project_Bank.git', credentialsId: 'github-creds']]
				])
			}
		}

		stage('Build') {
			steps {
				echo 'Building project...'
				sh 'mvn clean compile'
			}
		}

		stage('Run Tests') {
			steps {
				echo "Running ${params.TEST_SUITE} tests..."
				script {
					def testXml = ''
					if (params.TEST_SUITE == 'ui') {
						testXml = 'testng-ui.xml'
					} else if (params.TEST_SUITE == 'db') {
						testXml = 'testng-db.xml'
					} else if (params.TEST_SUITE == 'api') {
						testXml = 'testng-api.xml'
					} else {
						error "Unknown TEST_SUITE: ${params.TEST_SUITE}"
					}

					sh "mvn test -DsuiteXmlFile=${testXml}"
				}
			}
		}

		stage('Archive Reports') {
			steps {
				echo 'Archiving reports...'
				archiveArtifacts artifacts: 'reports/**/*.html', allowEmptyArchive: true
			}
		}

	}

	post {
		success {
			echo "Pipeline finished successfully!"
		}
		failure {
			echo "Pipeline failed. Check the logs for details."
		}
	}
}
