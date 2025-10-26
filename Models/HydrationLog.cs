namespace HydroQuestApi.Models
{
    public class HydrationLog
    {
        public int Id { get; set; }
        public string UserId { get; set; } = string.Empty;
        public int Amount { get; set; }
        public DateTime Timestamp { get; set; } = DateTime.UtcNow;
    }
}
