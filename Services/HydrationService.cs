using HydroQuestApi.Data;
using HydroQuestApi.Models;
using Microsoft.EntityFrameworkCore;

namespace HydroQuestApi.Services
{
    public class HydrationService
    {
        private readonly AppDbContext _context;

        public HydrationService(AppDbContext context)
        {
            _context = context;
        }

        public async Task LogIntake(string userId, int amount)
        {
            _context.HydrationLogs.Add(new HydrationLog { UserId = userId, Amount = amount });
            await _context.SaveChangesAsync();
        }

        public int GetDailyIntake(string userId)
        {
            var today = DateTime.UtcNow.Date;
            return _context.HydrationLogs
                .Where(l => l.UserId == userId && l.Timestamp.Date == today)
                .Sum(l => l.Amount);
        }
        public int GetDailyIntakeForDate(string userId, DateTime date)
        {
            return _context.HydrationLogs
                .Where(l => l.UserId == userId && l.Timestamp.Date == date)
                .Sum(l => l.Amount);
        }

    }
}
