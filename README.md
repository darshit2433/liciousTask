# liciousTask

to run the project 
1) start mongo db 
2 ) run the command 
mvn spring-boot:run
3) open the following link 
http://localhost:8080/swagger-ui.html#/


I hae created 2 APIs 
1) addUpdateSegment (to configure the existing  segment )
sample reuest 
{

	"segmantName" : "RED MEAT",
	"segmantRuleSets" : [
		{
			"key" : "gender",
			"oprator" : "EQ",
			"value" : "male"
		},
		{
			"oprator" : "OR",
			"oprations" : [
				{
					"key" : "age",
					"oprator" : "GT",
					"value" : "10"
				},
				{
					"oprator" : "AND",
					"oprations" : [
						{
							"key" : "ordersCount",
							"oprator" : "LT",
							"value" : "10"
						},
						{
							"key" : "meatPrefrance",
							"oprator" : "EQ",
							"value" : "RED"
						}
					]
				}
			]
		}
	]
}


2) getUserGroup (save user and get groups name)
{
	"age" : 24,
	"city" : "surat",
	"firstName" : "darshit",
	"gender" : "male",
	"lastName" : "padhya",
	"meatPrefrance" : "RED",
	"ordersCount" : 4,
  }



Here I have assume that count of segmants will always be between 5-10 so I have created system that will get all the segaments and dynamically check all rule sets and assign the segaments according to that 
