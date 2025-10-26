namespace HydroQuestApi.Models
{
    public class Gamification
    {
        public string UserId { get; set; } = string.Empty;  
        public int Streak { get; set; } = 0;
        public int CompanionLevel { get; set; } = 1;
        public string Achievements { get; set; } = string.Empty;  
    }
}
