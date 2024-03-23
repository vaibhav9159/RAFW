pipeline 
{
    agent any
    
    tools{
    	maven '3.8.6'
        }
        
    environment{
   
        BUILD_NUMBER = "${BUILD_NUMBER}"
   
    }
    

    stages 
    {
        stage('Build') 
        {
            steps
            {
                 git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                 sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post 
            {
                success
                {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }

        
        
        stage("Deploy to QA"){
            steps{
                echo("deploy to qa done")
            }
        }
             
             
                
                
      stage('Run Docker Image with Regression Tests') {
    steps {
        script {
            def suiteXmlFilePath = 'src/test/resources/TestRunners/testng.xml'
            def dockerCommand = """
                docker run --name apitesting${BUILD_NUMBER} \
                -v "${WORKSPACE}/reports:/app/reports" \
                vaibhavs07/apitest:latest \
                /bin/bash -c "mvn test -Dsurefire.suiteXmlFiles=${suiteXmlFilePath}"
            """
            
            def exitCode = sh(script: dockerCommand, returnStatus: true)
            
            if (exitCode != 0) {
                currentBuild.result = 'FAILURE'
            }
            sh "docker start apitesting${BUILD_NUMBER}"
            sh "docker cp apitesting${BUILD_NUMBER}:/app/target/APIExecutionReport.html ${WORKSPACE}/target"
            sh "docker rm -f apitesting${BUILD_NUMBER}"
        }
    }
}



		
		stage('Publish Regression Extent Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: false, 
                                  reportDir: 'target', 
                                  reportFiles: 'APIExecutionReport.html', 
                                  reportName: 'API HTML Regression Extent Report', 
                                  reportTitles: ''])
            }
        }
        
        
         

         
    }
}