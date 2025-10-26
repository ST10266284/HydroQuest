using Google.Apis.Auth;
using HydroQuestApi.Data;
using HydroQuestApi.Models;

namespace HydroQuestApi.Services
{
    public class UserService
    {
        private readonly AppDbContext _context;
        private readonly string _googleClientId;

        public UserService(AppDbContext context, IConfiguration config)
        {
            _context = context;
            _googleClientId = config["GoogleClientId"];
        }

        public async Task<User?> VerifyAndGetUser(string idToken)
        {
            var settings = new GoogleJsonWebSignature.ValidationSettings { Audience = new[] { _googleClientId } };
            var payload = await GoogleJsonWebSignature.ValidateAsync(idToken, settings);
            var googleId = payload.Subject;

            var user = await _context.Users.FindAsync(googleId);
            if (user == null)
            {
                user = new User { GoogleId = googleId };
                _context.Users.Add(user);
                _context.Gamifications.Add(new Gamification { UserId = googleId });
                await _context.SaveChangesAsync();
            }
            return user;
        }

        public async Task UpdateSettings(string googleId, int goal, string units, string theme)
        {
            var user = await _context.Users.FindAsync(googleId);
            if (user != null)
            {
                user.DailyGoal = goal;
                user.Units = units;
                user.Theme = theme;
                await _context.SaveChangesAsync();
            }
        }
    }
}
