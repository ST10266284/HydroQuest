namespace HydroQuestApi.Models
{
    public class User
    {
        public string GoogleId { get; set; } = string.Empty;  
        public int DailyGoal { get; set; } = 2000;
        public string Units { get; set; } = "ml";
        public string Theme { get; set; } = "light";
    }
}
