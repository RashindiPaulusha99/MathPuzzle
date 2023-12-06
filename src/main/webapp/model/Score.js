
class Score {
    constructor(id,playerId, questionLink, answer,level,isCorrect,score,reward) {
        this.id = id;
        this.playerId = playerId;
        this.questionLink = questionLink;
        this.answer = answer;
        this.level = level;
        this.isCorrect = isCorrect;
        this.score = score;
        this.reward = reward;
    }
}