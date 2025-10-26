using HydroQuestApi.Data;
using HydroQuestApi.Models;
using Microsoft.EntityFrameworkCore;

namespace HydroQuestApi.Services
{
    public class GamificationService
    {
        private readonly AppDbContext _context;

        public GamificationService(AppDbContext context)
        {
            _context = context;
        }

        public async Task UpdateGamification(string userId, int amount)
        {
            var game = await _context.Gamifications.FindAsync(userId);
            if (game != null)
            {
                var today = DateTime.UtcNow.Date;
                var dailyIntake = _context.HydrationLogs
                    .Where(l => l.UserId == userId && l.Timestamp.Date == today)
                    .Sum(l => l.Amount);

                if (dailyIntake >= 2000) game.Streak++;
                game.CompanionLevel = (dailyIntake / 1000) + 1;
                if (game.Streak == 7) game.Achievements += ",Week Warrior";
                await _context.SaveChangesAsync();
            }
        }

        public Gamification? GetGamification(string userId)
        {
            return _context.Gamifications.Find(userId);
        }
    }
}
