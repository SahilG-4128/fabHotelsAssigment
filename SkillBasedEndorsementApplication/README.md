# SkillEndrosementProject

Api Contract:-

    1) Post Endorsement API:-

        it is a POST Api Allows a reviewer to endorse a reviewee for a specific skill with a score, 
        and returns a weighted relevance score considering the reviewee's and reviewer's profiles.
    
        REST Method:- POST
        end point:- /endorsements
        Resquest Body:-  
                    {
                        "reviewerId": 1,
                        "revieweeId": 2,
                        "skill": "Java",
                        "score": 6
                    }

        Response Body:-
                    {
                        "id": 64,
                        "revieweeId": 1,
                        "reviewerId": 2,
                        "skill": "Java",
                        "score": 6,
                        "adjustedScore": 6.0,
                        "reason": "Reviewer has some experience of:Java"
                    }

    2) Get Endorsement API:-

        it is a GET Method which Retrieves all the endorsements for a user, displaying each 
        skill with the scores rated by different persons alongside a system-calculated weighted score.

        REST Method:- GET
        end point:- /endorsements/{userId}
        Resquest Body:- Not Required

        Response Body:-
                        {
                            "Java": [
                                        "P1 - 6 (5.4 with reason: Calculated based on skill relevance, coworker status, and experience.)",
                                        "P2 - 6 (0.0 with reason: Reviewer id: 2 does not have skill: Java So,cannot review it.)",
                                        "P2 - 6 (0.0 with reason: Reviewer id: 2 does not have skill: Java So,cannot review it.)",
                                        "P2 - 6 (10.200000000000001 with reason: Reviewee has some experience of:Java, Reviewer has some experience of:Java)",
                                        "P2 - 6 (8.399999999999999 with reason: Reviewer has some experience of:Java)",
                                        "P2 - 6 (6.0 with reason: Reviewer has some experience of:Java)",
                                        "P2 - 6 (6.0 with reason: Reviewer has some experience of:Java)"
                                    ],
                            "Sales": [
                                        "P2 - 6 (0.0 with reason: Reviewer id: 2 does not have skill: Sales So,cannot review it.)"
                                    ]
                        }
                    
        
            
        
